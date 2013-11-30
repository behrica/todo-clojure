(ns todo.db
  (:require [korma.db :refer :all]
        [korma.core :refer :all]
        [todo.core :refer :all]
        [todo.dbschema :refer :all]
        [clj-time.core :refer [date-time]]
        [clj-time.coerce :refer [to-long]]
        [todo.joda :refer [with-joda-time-reader]]
        )
  (import java.sql.Date)
  )

;(def db (h2 {:db "/tmp/korma.db"}))

(defdb db {:classname   "org.h2.Drivera"
             :subprotocol "h2"
             :subname "mem:test"
             :user     "sa"
             :password ""
})

(defentity TODOEVENT)
(update-db)

(defn first-val [map]
  (first (vals map))
     )

(defn add-todo [{:keys [title date] :as todo}]

  (first-val (insert TODOEVENT (values [{:ID   nil
                                         :TYPE "todo-created"
                                         :EDN  (pr-str todo)
                                         }])))
  )


(defn todos []
  (with-joda-time-reader
    (map #(read-string (:EDN %)) (select TODOEVENT))))


(defn init-db []
  (println "!! Initialize db for tests !!")
  (delete TODOEVENT)
  (add-todo (new-todo "newTitle" (clj-time.core/date-time 2020 01 01)))
  (add-todo (new-todo "newTitle" (clj-time.core/date-time 2020 01 02)))
)



