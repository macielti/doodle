(ns doodle.wire.in.notification
  (:require [schema.core :as s]
            [common-clj.schema.core :as schema]))

;TODO: verify is the skeleton pattern naming is used only for datomic entities
(def notification-message-skeleton
  {:topic (s/enum :notification)
   :value {:email   s/Str
           :title   s/Str
           :content s/Str}})

(s/defschema NotificationMessage
  (schema/loose-schema notification-message-skeleton))
