(ns todo.db
  (:refer-clojure :exclude [read-string])
  (:require [korma.db :refer [defdb]]
            [korma.core :refer [defentity insert values delete select]]
            [todo.core :refer :all]
            [todo.dbschema :refer :all]
            [clj-time.core :refer [date-time]]
            [clj-time.coerce :refer [to-long]]
            [todo.joda :refer [with-joda-time-reader]]
            [clojure.instant :as i]
            [clojure.edn :refer [read-string]]
            )
  (import java.sql.Date)
  )





;(def db (h2 {:db "/tmp/korma.db"}))

(defdb db {:classname   "org.h2.Drivera"
           :subprotocol "h2"
           :subname     "mem:test"
           :user        "sa"
           :password    ""
           })

(defentity TODOEVENT)
(update-db)

(defn first-val [map]
  (first (vals map))
  )

(defn read-string-with-joda [s]
  (with-joda-time-reader
    (read-string s)))

(defn add-todo [{:keys [title date] :as todo}]

  (first-val (insert TODOEVENT (values [{
                                          :TYPE "todo-created"
                                          :EDN  (pr-str todo)
                                          }]))))

(defn delete-todo [id]
  (insert TODOEVENT (values [{
                               :TYPE "todo-deleted"
                               :EDN  (pr-str id)
                               }]))
  )

(defn was-deleted? [id todo-events]
  (not (empty? (filter #(and (= (:TYPE %) "todo-deleted")
                             (= (read-string-with-joda (:EDN %)) id))
                       todo-events)))

  )

(defn apply-event-to-world [event world]
  (condp = (:TYPE event)
    "todo-created" (conj world (read-string (:EDN event)))
    "todo-deleted" (let [id (read-string (:EDN event))]
                     (remove #(= (:uuid %) id) world))))




(defn aggregate-todos [todo-events world]
  (if (empty? todo-events)
    world
    (aggregate-todos (rest todo-events)
                     (apply-event-to-world (first todo-events) world))))

(defn todos []
  (aggregate-todos (select TODOEVENT) []))


(defn init-db []
  (println "!! Initialize db for tests !!")
  (delete TODOEVENT)
  (add-todo (new-todo "newTitle" (clj-time.core/date-time 2020 01 01) "1"))
  (add-todo (new-todo "newTitle" (clj-time.core/date-time 2020 01 02) "2"))
  )



