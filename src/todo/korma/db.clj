(ns todo.korma.db
  (require
    [todo.core :refer :all]
    [korma.db :refer [defdb]]
    [korma.core :refer [defentity insert values delete select]]
    [todo.korma.dbschema :refer [update-db]]
     ))

(defn- first-val [map]
  (first (vals map))
  )


(defentity TODOEVENT)

;(def db (h2 {:db "/tmp/korma.db"}))

(defdb db {:classname   "org.h2.Drivera"
           :subprotocol "h2"
           :subname     "mem:test"
           :user        "sa"
           :password    ""
           })

(update-db)

(defn add-todo-created-event [{:keys [title date] :as todo}]

  (first-val (insert TODOEVENT (values [{
                                          :TYPE "todo-created"
                                          :EDN  (pr-str todo)
                                          }]))))

(defn add-todo-deleted-event [id]
  (insert TODOEVENT (values [{
                               :TYPE "todo-deleted"
                               :EDN  (pr-str id)
                               }])))

(defn find-todo-events []
  (select TODOEVENT))


(defn delete-all-events []
  (delete TODOEVENT))

(defn init-db []
  (println "!! Initialize db for tests !!")
  (delete-all-events)
  (add-todo-created-event (new-todo "newTitle" (clj-time.core/date-time 2020 01 01) "1"))
  (add-todo-created-event (new-todo "newTitle" (clj-time.core/date-time 2020 01 02) "2")))
