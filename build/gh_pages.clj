(ns build.gh-pages
  (:require
   [clojure.tools.build.api :as build]
   ))


(defn clean
  [_]
  (build/delete {:path "target"}))


(defn all
  [_]
  (build/copy-dir
    {:src-dirs   ["target"]
     :target-dir "gh-pages"}))
