(ns doodle.diplomatic.consumer
  (:require [schema.core :as s]
            [sendgrid.core :as sg]
            [clojure.tools.logging :as log]
            [doodle.wire.in.notification :as wire.in.notification]
            [doodle.adapters.send-grid.notification :as adapters.send-grid.notification])
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
