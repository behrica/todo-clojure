(ns todo.db-test
  (:require [clojure.test :refer :all ]
            [todo.db :refer :all ]
            [korma.db :refer :all ]
            [korma.core :refer :all ]
            [todo.core :refer :all ]
            [clj-time.core :only [date-time]]
            [clj-time.coerce :only [to-long]]
            [midje.sweet :refer :all ])
  (:import java.sql.Date))

(def a-date (clj-time.core/date-time 2020 01 01) )

(defn addTodo []
  (add-todo (struct todo "newTitle" a-date))
  (let [query-result (exec-raw ["SELECT * FROM TODOS"] :results )]
    [(count query-result)
     (:TITLE (first query-result))
     (:DATE (first query-result))
     ]
    )
)


(with-state-changes [(before :facts (delete TODOS))]
  (fact "adding a todo result in one row in todos table"
;    (addTodo) => [1 "newTitle" (java.sql.Date. (clj-time.coerce/to-long a-date))]
    ))

