(ns todo.run)

(use 'ring.adapter.jetty)
(require '[todo.web :as web])

(run-jetty #'web/app {:port 8080})
