(ns release
  (:require
   [clojure.string :as str]
   [clojure.java.io :as jio]
   [clojure.java.shell :as jsh]
   [reitit.core :as reitit]
   [reitit.ring]
   [muuntaja.core :as muuntaja]
   [reitit.ring.middleware.parameters]
   [reitit.ring.middleware.muuntaja]
   [runner.web.server-render :as server-render]
   [env]
   [page.index]
   ))


(def ^:const +target+ "target")


(defonce body-script-modules-reference (server-render/body-script-modules-reference (:server-render/body-script-modules-file env/release)))
(defonce webpack-asset-manifest-reference (server-render/webpack-asset-manifest-reference (:server-render/webpack-asset-manifest-file env/release)))


(def app
  (reitit.ring/ring-handler
    (reitit.ring/router
      (reitit/routes page.index/router)
      {:data {:system/web-dir                       (:system/web-dir env/release)
              :server-render/body-script-modules    body-script-modules-reference
              :server-render/webpack-asset-manifest webpack-asset-manifest-reference

              :muuntaja   muuntaja/instance
              :middleware [reitit.ring.middleware.muuntaja/format-middleware]}})))


(defn- strip-left-slash
  "Return just path(slashed) if path is absolute, otherwise return path(slashed) with parent."
  [s]
  (if (str/starts-with? s "/")
    (subs s 1)
    s))


(defn- index-route?
  [[path _data :as _route]]
  (or (str/ends-with? path "/")
      (str/ends-with? path "/index.html")
      (str/ends-with? path "/index.htm")))


(defn- index-html-file
  [parent path]
  (jio/file
    parent
    (strip-left-slash
      (if (str/ends-with? path "/")
        (str path "index.html")
        path))))


(defn generate-html
  [router]
  (run!
    (fn [[path]]
      (let [{:keys [body]} (app {:uri path :request-method :get})
            target (index-html-file +target+ path)]
        (jio/make-parents target)
        (spit target body)
        (println "Generated:" (str target))))
    (->> router (reitit/compiled-routes) (filter index-route?))))


(defn build
  [{:as _options}]
  (try
    (generate-html (reitit.ring/get-router app))
    (finally
      (System/exit 0))))


(comment
  (map (fn [[path]] path) (reitit/routes page.index/router))
  (app {:uri "/" :request-method :get})
  )
