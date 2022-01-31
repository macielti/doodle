(ns doodle.adapters.send-grid.notification
  (:require [schema.core :as s]
            [doodle.wire.in.notification :as wire.in.notification]))

(s/defn ->wire
  [{:keys [value]} :- wire.in.notification/NotificationMessage
   company-email :- s/Str
   send-grid-key :- s/Str]
  {:api-token (str "Bearer " send-grid-key)
   :from      company-email
   :to        (:email value)
   :subject   (:title value)
   :message   (:content value)})
