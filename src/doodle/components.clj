(ns doodle.components
  (:require [com.stuartsierra.component :as component]
            [common-clj.component.config :as component.config]
            [common-clj.component.kafka.consumer :as component.consumer]
            [doodle.diplomatic.consumer :as diplomatic.consumer]))

(def system
  (component/system-map
    :config (component.config/new-config "resources/config.json" :prod)
    :consumer (component/using (component.consumer/new-consumer diplomatic.consumer/topic-consumers) [:config])))

(def system-test
  (component/system-map
    :config (component.config/new-config "resources/config.example.json" :test)
    :consumer (component/using (component.consumer/new-consumer diplomatic.consumer/topic-consumers) [:config])))

(defn start-system! []
  (component/start system))
