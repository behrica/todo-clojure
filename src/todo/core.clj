(ns todo.core
  (:require
    [clojure.string :refer [split]]
    [clj-time.core :refer [date-time month year DateTimeProtocol]])
)


(defn uuid [] (java.util.UUID/randomUUID))

(defstruct todo :title :date :uuid)

(defn new-todo [title date uuid]
  {:pre [(satisfies? DateTimeProtocol date)]}
  (struct todo title date uuid)
)


(defn- month-equal? [todo a-month]
  (let [todo-date (:date todo)]
    (and
      (= (month todo-date) (month a-month))
      (= (year todo-date) (year a-month)))))

(defn todos-in-month [todos month]
  (filter #(month-equal? % month) todos))
