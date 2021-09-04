(ns microservice-notification.config
  (:use [clojure pprint])
  (:require [microservice-notification.adapters.common :as adapters.common]
            [com.stuartsierra.component :as component]
            [cheshire.core :as json]
            [environ.core :as environ]))

(defrecord Config []
  component/Lifecycle
  (start [this]
    (let [config (json/parse-string (slurp (or (environ/env :clj-config-path) "resources/config.json"))
                                    adapters.common/str->keyword-kebab-case)]
      (assoc this :config config)))

  (stop [this]
    (assoc this :config nil)))

(defn new-config []
  (->Config))
