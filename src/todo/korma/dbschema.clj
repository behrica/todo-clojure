(ns todo.korma.dbschema
  (:require
    [clj-dbcp.core        :as cp]
    [clj-liquibase.change :as ch]
    [clj-liquibase.cli    :as cli]
    [clj-jdbcutil.core    :as sp]
    [clj-liquibase.core :refer [defchangelog update with-lb]]
    ))

(defchangelog app-changelog "todo" [
["id=1" "author=behrica" [(ch/create-table :TODOS [[:id :int :null false :pk true :autoinc true]])]]
["id=2" "author=behrica" [(ch/add-columns :TODOS [[:TITLE [:varchar 40]][:DATE :date]])]]
["id=3" "author=behrica" [(ch/add-columns :TODOS [[:UUID [:varchar 40 ] :null false]])
                          (ch/add-unique-constraint :TODOS [:UUID] "uuid_unique")]]
["id=4" "author=behrica" [(ch/drop-unique-constraint :TODOS "uuid_unique")]]
["id=5" "author=behrica" [(ch/drop-not-null-constraint :TODOS :UUID :column-data-type [:varchar 40])]]
["id=6" "author=behrica" [(ch/drop-column :TODOS :UUID )]]
["id=7" "author=behrica" [(ch/create-table :TODOEVENT [[:id :int :null false :pk true :autoinc true]])]]
["id=8" "author=behrica" [(ch/add-columns :TODOEVENT [[:TYPE [:varchar 255]]])]]
["id=9" "author=behrica" [(ch/add-columns :TODOEVENT [[:EDN [:varchar 500]]])]]
["id=10" "author=behrica" [(ch/drop-table :TODOS )]]

 ])


(def jdbc-url "jdbc:h2:mem:test")

(def ds (cp/make-datasource  {:classname "org.h2.Driver" 
                              :jdbc-url jdbc-url
                              :username "sa"
                              :password ""}))

(defn update-db []
  (sp/with-connection {:datasource ds}
    (with-lb (update app-changelog)))

)

