(ns todo.repository
  (:refer-clojure :exclude [read-string])
  (:require [todo.core :refer :all]
            [todo.korma.dbschema :refer :all]
            [todo.korma.db :refer :all]
            [clj-time.core :refer [date-time now]]
            [clj-time.coerce :refer [to-long]]
            [todo.joda :refer [with-joda-time-reader ]]
            [clojure.instant :as i]
            [clojure.edn :refer [read-string]]
            )
  (import java.sql.Date))



(defn- replace-todo [new-todo world]
  (conj (remove #(= (:uuid %) (:uuid new-todo)) world) new-todo))

(defn- apply-event-to-world [event-string world]
  (let [event (read-string (:EDN event-string))]
    (condp = (:TYPE event-string)
        "todo-created" (conj world event)
        "todo-deleted" (remove #(= (:uuid %) event) world)
        "todo-changed" (replace-todo event world))))


(defn- aggregate-todos [todo-events world]
  (if (empty? todo-events)
    world
    (recur (rest todo-events)
           (apply-event-to-world (first todo-events) world))))

(defn todos []
  (aggregate-todos (find-todo-events) []))


(defn add-todo [title]
  (add-todo-created-event (new-todo title (now) (uuid))))






