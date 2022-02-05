(ns doodle.components
  (:require [com.stuartsierra.component :as component]
            [common-clj.component.config :as component.config]
            [common-clj.component.kafka.producer :as component.kafka.producer]
            [common-clj.component.kafka.consumer :as component.kafka.consumer]
            [doodle.diplomatic.consumer :as diplomatic.consumer]))

(def system
  (component/system-map
    :config (component.config/new-config "resources/config.json" :prod)
    :consumer (component/using (component.kafka.consumer/new-consumer diplomatic.consumer/topic-consumers) [:config])))

(def system-test
  (component/system-map
    :config (component.config/new-config "resources/config.example.json" :test)
    :consumer (component/using (component.kafka.consumer/new-mock-consumer diplomatic.consumer/topic-consumers) [:config])
    :producer (component/using (component.kafka.producer/new-mock-producer) [:config :consumer])))

(defn start-system! []
  (component/start system))
