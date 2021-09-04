(ns microservice-notification.diplomatic.consumer
  (:use [clojure pprint])
  (:require [microservice-notification.wire.in.notification :as wire.in.notification]
            [microservice-notification.adapters.send-grid.notification :as adapters.send-grid.notification]
            [clojure.tools.logging :as log]
            [sendgrid.core :as sg]
            [schema.core :as s]))

(s/defn send-notification!
  [{:keys [value] :as message} :- wire.in.notification/NotificationMessage
   {{:keys [send-grid-key company-email]} :config}]
  (s/validate wire.in.notification/NotificationMessage message)
  (sg/send-email (adapters.send-grid.notification/->wire message
                                                         company-email
                                                         send-grid-key))
  (log/info message))

(def topic-consumers
  {:notification {:schema  wire.in.notification/NotificationMessage
                  :handler send-notification!}})
