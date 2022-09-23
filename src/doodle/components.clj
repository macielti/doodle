(ns doodle.components
  (:require [com.stuartsierra.component :as component]
            [common-clj.component.config :as component.config]
            [common-clj.component.kafka.producer :as component.kafka.producer]
            [common-clj.component.kafka.consumer :as component.kafka.consumer]
            [doodle.diplomatic.consumer :as diplomatic.consumer]))

(def system
  (component/system-map
    :config (component.config/new-config "resources/config.edn" :prod :edn)
    :consumer (component/using (component.kafka.consumer/new-consumer diplomatic.consumer/topic-consumers) [:config])))

(def system-test
  (component/system-map
    :config (component.config/new-config "resources/config.example.edn" :test :edn)
    :producer (component/using (component.kafka.producer/new-mock-producer) [:config])
    :consumer (component/using (component.kafka.consumer/new-mock-consumer diplomatic.consumer/topic-consumers) [:config :producer])))

(defn start-system! []
  (component/start system))
