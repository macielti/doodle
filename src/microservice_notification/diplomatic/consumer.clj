(ns microservice-notification.diplomatic.consumer
  (:use [clojure pprint])
  (:require [microservice-notification.wire.in.notification :as wire.in.notification]
            [clojure.tools.logging :as log]
            [sendgrid.core :as sg]))

(defn send-notification!
  [{:keys [topic value] :as message} :- wire.in.notification/NotificationMessage
   {:keys []} :config]
  (sg/send-email)
  (log/info message))

(def topic-consumers
  {:notification {:schema  nil
                  :handler send-notification!}})
