(ns todo.db-test
  (:require [clojure.test :refer :all ]
            [todo.db :refer :all ]
            [korma.db :refer :all ]
            [korma.core :refer :all ]
            [todo.core :refer :all ]
            [clj-time.core :refer [date-time]]
            [clj-time.coerce :refer [to-long]]
            [midje.sweet :refer :all ])
  (:import java.sql.Date))

(def a-date (clj-time.core/date-time 2020 01 01) )


(with-state-changes [(before :facts (delete TODOEVENT))]
                    (fact "adding a todo result in one row in todos table"
                          (add-todo (new-todo "newTitle" a-date)) => number?
                          (let [query-result (exec-raw ["SELECT * FROM TODOEVENT"] :results)
                                first-event (first query-result)
                                ]
                            (count query-result) => 1
                            (:TYPE first-event) => "todo-created"
                            (:EDN first-event) => "{:title \"newTitle\", :date #inst \"2020-01-01T00:00:00.000-00:00\"}"
                            )))

(with-state-changes [(before :facts
                             (do
                               (delete TODOEVENT)
                               (insert TODOEVENT (values [{:TYPE "todo-created" :EDN "{:title \"newTitle\", :date #inst \"2020-01-01T00:00:00.000-00:00\"}"}])))
                             )]
                    (fact "select todos uses todo event"
                          (count (todos)) => 1
                          (:title (first (todos))) => "newTitle"
                          (to-long (:date (first (todos)))) => (to-long a-date)
                          )

                    )

