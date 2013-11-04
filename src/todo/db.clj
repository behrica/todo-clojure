(ns todo.db
  (:use [korma.db :refer :all]
        [korma.core :refer :all]
        [todo.core :refer :all]
        [todo.dbschema :refer :all]
        [clj-time.core :only [date-time]]
        [clj-time.coerce :only [to-long]]
        )
  (import java.sql.Date)
  )

(def db (h2 {:db "/tmp/korma.db"}))
(defdb korma-db db)
(defentity TODOS)
(update-db)

(defn add-todo [{:keys [title date]}]
  (insert TODOS
    (values [{:TITLE title :DATE (java.sql.Date. (to-long date))}]))
)

(defn todos []
  (select TODOS)
)


