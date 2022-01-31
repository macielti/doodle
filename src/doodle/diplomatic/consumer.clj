(ns microservice-notification.diplomatic.consumer
  (:require [microservice-notification.wire.in.notification :as wire.in.notification]
            [microservice-notification.adapters.send-grid.notification :as adapters.send-grid.notification]
            [clojure.tools.logging :as log]
            [sendgrid.core :as sg]
            [schema.core :as s])
  (:import (clojure.lang ExceptionInfo)))

(s/defn send-notification!
  [{:keys [value] :as message} :- wire.in.notification/NotificationMessage
   {{:keys [send-grid-key company-email]} :config}]
  (try (s/validate wire.in.notification/NotificationMessage message)
       (catch ExceptionInfo e
         (log/error e)))
  (let [result (sg/send-email (adapters.send-grid.notification/->wire message
                                                                      company-email
                                                                      send-grid-key))]
    (log/info message result)))

(def topic-consumers
  {:notification {:schema  wire.in.notification/NotificationMessage
                  :handler send-notification!}})
