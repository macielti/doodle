(ns doodle.adapters.send-grid.notification
  (:require [schema.core :as s]
            [doodle.wire.in.notification :as wire.in.notification]))

(s/defn ->wire
  [{:keys [message]} :- wire.in.notification/NotificationDocument
   company-email :- s/Str
   send-grid-key :- s/Str]
  {:api-token (str "Bearer " send-grid-key)
   :from      company-email
   :to        (:email message)
   :subject   (:title message)
   :message   (:content message)})
