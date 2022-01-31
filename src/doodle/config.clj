(ns doodle.config
  (:require [cheshire.core :as json]
            [environ.core :as environ]
            [clojure.tools.logging :as log]
            [doodle.adapters.common :as adapters.common]
            [com.stuartsierra.component :as component]))

(defrecord Config []
  component/Lifecycle
  (start [this]
    (let [config-path (or (environ/env :clj-config-path) "resources/config.json")
          config      (json/parse-string (slurp config-path)
                                         adapters.common/str->keyword-kebab-case)]
      (log/info :config-path config-path)
      (assoc this :config config)))
  (stop [this]
    (assoc this :config nil)))

(defn new-config []
  (->Config))
