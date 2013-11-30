(ns todo.db
  (:require [korma.db :refer :all]
        [korma.core :refer :all]
        [todo.core :refer :all]
        [todo.dbschema :refer :all]
        [clj-time.core :refer [date-time]]
        [clj-time.coerce :refer [to-long]]
        
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

(defentity TODOS)
(update-db)

(defn add-todo [{:keys [title date]}]
  (first (vals (insert TODOS
    (values [{:TITLE title :DATE (java.sql.Date. (to-long date))}]))))
  )

(defn todos []
  (select TODOS)
)

(defn init-db []
  (println "!! Initialize db for tests !!")
  (delete TODOS)
  (add-todo (new-todo "newTitle" (clj-time.core/date-time 2020 01 01)))
  (add-todo (new-todo "newTitle" (clj-time.core/date-time 2020 01 02)))
)



