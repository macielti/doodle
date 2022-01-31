(ns doodle.components
  (:require [com.stuartsierra.component :as component]
            [doodle.config :as config]
            [doodle.consumer :as consumer]))

(defn component-system []
  (component/system-map
    :config (config/new-config)
    :consumer (component/using (consumer/new-consumer) [:config])))

(defn start-system! []
  (component/start (component-system)))
