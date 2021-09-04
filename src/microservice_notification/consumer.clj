(ns microservice-notification.consumer
  (:use [clojure pprint])
  (:require [com.stuartsierra.component :as component]
            [schema.core :as s]
            [microservice-notification.diplomatic.consumer :as diplomatic.consumer]
            [io.pedestal.interceptor :as interceptor]
            [io.pedestal.interceptor.chain :as chain]
            [cheshire.core :as json]
            [clojure.tools.logging :as log])
  (:import (org.apache.kafka.common.serialization StringDeserializer)
           (org.apache.kafka.clients.consumer KafkaConsumer ConsumerRecord)))

(def kafka-client-starter
  (interceptor/interceptor
    {:name  ::kafka-client
     :enter (fn [{:keys [consumer-props] :as context}]
              (assoc context :kafka-client (new KafkaConsumer consumer-props)))}))

(def subscriber
  (interceptor/interceptor
    {:name  ::subscriber
     :enter (fn [{:keys [topics kafka-client] :as context}]
              (.subscribe kafka-client topics)
              context)}))

(s/defn kafka-record->clj-message
  [record :- ConsumerRecord]
  {:topic (keyword (.topic record))
   :value (json/decode (.value record) true)})

(s/defn handler-by-topic
  [topic :- s/Keyword
   topic-consumers]
  (topic topic-consumers))

(def kafka-consumer!
  (interceptor/interceptor
    {:name  ::kafka-consumer
     :enter (fn [{:keys [kafka-client topic-consumers components] :as context}]
              (future
                (try
                  (while true
                    (let [records (seq (.poll kafka-client 100))]
                      (doseq [record records]
                        (let [{:keys [topic] :as message} (-> record
                                                              kafka-record->clj-message)
                              {:keys [handler]} (handler-by-topic topic topic-consumers)]
                          (handler message components)))))
                  (catch Exception error
                    (log/error error)
                    (.close kafka-client))))
              context)}))

(s/defrecord Consumer [config]
  component/Lifecycle

  (start [this]
    (let [consumer-props {"value.deserializer" StringDeserializer
                          "key.deserializer"   StringDeserializer
                          "bootstrap.servers"  (get-in config [:config :bootstrap-server])
                          "group.id"           (get-in config [:config :service-name])}
          components     {:config (:config config)}
          topics         (get-in config [:config :topics])
          context        {:consumer-props  consumer-props
                          :topics          topics
                          :topic-consumers diplomatic.consumer/topic-consumers
                          :components      components}
          {:keys [kafka-client]} (chain/execute context [kafka-client-starter subscriber kafka-consumer!])]
      (assoc this :kafka-client kafka-client)))

  (stop [{:keys [kafka-client] :as this}]
    (.close kafka-client)
    (assoc this :kafka-client nil)))

(defn new-consumer []
  (->Consumer {}))
