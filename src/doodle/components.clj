(ns microservice-notification.components
  (:require [com.stuartsierra.component :as component]
            [microservice-notification.config :as config]
            [microservice-notification.consumer :as consumer]))

(defn component-system []
  (component/system-map
    :config (config/new-config)
    :consumer (component/using (consumer/new-consumer) [:config])))

(defn start-system! []
  (component/start (component-system)))
