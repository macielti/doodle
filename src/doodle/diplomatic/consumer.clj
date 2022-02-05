(ns doodle.diplomatic.consumer
  (:require [schema.core :as s]
            [sendgrid.core :as sg]
            [clojure.tools.logging :as log]
            [doodle.wire.in.notification :as wire.in.notification]
            [doodle.adapters.send-grid.notification :as adapters.send-grid.notification])
  (:import (clojure.lang ExceptionInfo)))

(s/defn send-notification!
  [{:keys [message] :as document} :- wire.in.notification/NotificationDocument
   {{:keys [send-grid-key company-email]} :config}]
  (try (s/validate wire.in.notification/NotificationDocument document)
       (catch ExceptionInfo e
         (log/error e)))
  (let [result (sg/send-email (adapters.send-grid.notification/->wire document
                                                                      company-email
                                                                      send-grid-key))]
    (log/info document result)))

(def topic-consumers
  {:notification {:schema  wire.in.notification/NotificationDocument
                  :handler send-notification!}})
