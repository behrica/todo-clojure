(ns todo.core
  (:require
    [clojure.string :refer [split]]
    [clj-time.core :refer [date-time month year DateTimeProtocol]])
)


(defstruct todo :title :date)

(defn new-todo [title date]
 {:pre [(satisfies? DateTimeProtocol date)]}
  (struct todo title date)
)


(defn- month-equal? [todo a-month]
  (let [todo-date (:date todo)]
    (and
      (= (month todo-date) (month a-month))
      (= (year todo-date) (year a-month))
      )))

(defn todos-in-month [todos month]
  (filter #(month-equal? % month) todos))
