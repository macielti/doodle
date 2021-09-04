(ns microservice-notification.diplomatic.consumer
  (:use [clojure pprint])
  (:require [clojure.tools.logging :as log]))

(defn send-notification!
  [{:keys [topic value] :as message}
   _]
  (log/info message))

(def topic-consumers
  {:notification {:schema  nil
                  :handler send-notification!}})
