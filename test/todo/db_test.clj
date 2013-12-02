(ns todo.db-test
  (:require [clojure.test :refer :all ]
            [todo.db :refer :all ]
            [korma.db :refer :all ]
            [korma.core :refer :all ]
            [todo.core :refer :all ]
            [clj-time.core :refer [date-time]]
            [clj-time.coerce :refer [to-long]]
            [midje.sweet :refer :all ])
  (:import java.sql.Date (java.util UUID)))

(def a-date (clj-time.core/date-time 2020 01 01) )


(with-state-changes [(before :facts (delete TODOEVENT))]
                    (fact "adding a todo result in one row in todoevent table"
                          (add-todo (new-todo "newTitle" a-date (UUID/fromString "59ac5f78-e691-4351-9652-11d6820cdc09"))) => number?
                          (let [query-result (exec-raw ["SELECT * FROM TODOEVENT"] :results)
                                first-event (first query-result)
                                ]
                            (count query-result) => 1
                            (:TYPE first-event) => "todo-created"
                            (:EDN first-event) => "{:title \"newTitle\", :date #inst \"2020-01-01T00:00:00.000-00:00\", :uuid #uuid \"59ac5f78-e691-4351-9652-11d6820cdc09\"}"
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
(with-state-changes [(before :facts
                             (do
                               (delete TODOEVENT)
                               (insert TODOEVENT (values [{:ID 1 :TYPE "todo-created" :EDN "{:title \"newTitle\", :date #inst \"2020-01-01T00:00:00.000-00:00\"}"}])))
                             )]

                    (fact "delete todos adds delete event"
                          (delete-todo 1)
                          (select-keys (second (exec-raw ["SELECT * FROM TODOEVENT"] :results)) [:TYPE :EDN]) => {:TYPE "todo-deleted" :EDN "1" }
                          ))


(with-state-changes [(before :facts
                             (do
                               (delete TODOEVENT)
                               (insert TODOEVENT (values [{:ID 1 :TYPE "todo-created" :EDN "{:title \"newTitle\", :date #inst \"2020-01-01T00:00:00.000-00:00\", :uuid #uuid \"121e34fc-4239-40d7-ab52-4b71b3d0d95d\"}"}]))
                               (insert TODOEVENT (values [{:ID 2 :TYPE "todo-deleted" :EDN "#uuid \"121e34fc-4239-40d7-ab52-4b71b3d0d95d\""}])))
                             )]

                    (fact "select todos aggregates events created/delete"
                          (todos) => []
                          ))

(with-state-changes [(before :facts
                             (do
                               (delete TODOEVENT)
                               (insert TODOEVENT (values [{:ID 1 :TYPE "todo-created" :EDN "{:title \"newTitle-1\", :date #inst \"2020-01-01T00:00:00.000-00:00\", :uuid #uuid \"121e34fc-4239-40d7-ab52-4b71b3d0d95d\"}"}]))
                               (insert TODOEVENT (values [{:ID 2 :TYPE "todo-deleted" :EDN "#uuid \"121e34fc-4239-40d7-ab52-4b71b3d0d95d\""}]))
                               (insert TODOEVENT (values [{:ID 3 :TYPE "todo-created" :EDN "{:title \"newTitle-2\", :date #inst \"2020-01-01T00:00:00.000-00:00\", :uuid #uuid \"781e34fc-4239-40d7-ab52-4b71b3d0d95d\"}"}])))

                             )]

                    (fact "select todos aggregates events created/delete/create"
                          (todos) => [{:date #inst "2020-01-01T00:00:00.000-00:00", :title "newTitle-2", :uuid #uuid "781e34fc-4239-40d7-ab52-4b71b3d0d95d"}]
                          ))


(with-state-changes [(before :facts
                             (do
                               (delete TODOEVENT)
                               (insert TODOEVENT (values [{:ID 1 :TYPE "todo-created" :EDN "{:title \"newTitle\", :date #inst \"2020-01-01T00:00:00.000-00:00\", :uuid #uuid \"121e34fc-4239-40d7-ab52-4b71b3d0d95d\"}"}]))
                               (insert TODOEVENT (values [{:ID 2 :TYPE "todo-changed" :EDN "{:title \"newTitle-modified\", :date #inst \"2099-01-01T00:00:00.000-00:00\", :uuid #uuid \"121e34fc-4239-40d7-ab52-4b71b3d0d95d\"}"}])))
                             )]

                    (fact "select todos aggregates events created/delete"
                          (todos) => [{:date #inst "2099-01-01T00:00:00.000-00:00", :title "newTitle-modified", :uuid #uuid "121e34fc-4239-40d7-ab52-4b71b3d0d95d"}]
                          ))
