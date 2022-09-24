(ns doodle.diplomatic.consumer
  (:require [schema.core :as s]
            [sendgrid.core :as sg]
            [doodle.wire.in.notification :as wire.in.notification]
            [doodle.adapters.send-grid.notification :as adapters.send-grid.notification]
            [taoensso.timbre :as timbre]))

(s/defn send-notification!
  [message-document :- wire.in.notification/NotificationDocument
   {{:keys [send-grid-key company-email]} :config}]
  (let [result (sg/send-email (adapters.send-grid.notification/->wire message-document
                                                                      company-email
                                                                      send-grid-key))]
    (timbre/info message-document result)))

(def topic-consumers
  {:notification {:schema  wire.in.notification/NotificationDocument
                  :handler send-notification!}})
