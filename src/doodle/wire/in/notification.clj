(ns doodle.wire.in.notification
  (:require [schema.core :as s]
            [common-clj.schema.core :as schema]))

;TODO: verify is the skeleton pattern naming is used only for datomic entities
(def notification-document-skeleton
  {:email   s/Str
   :title   s/Str
   :content s/Str})

(s/defschema NotificationDocument
  (schema/loose-schema notification-document-skeleton))
