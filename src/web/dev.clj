(ns dev
  (:require
   [clojure.java.io :as jio]
   [rum.core :as rum]
   [citrus.core :as citrus]
   [muuntaja.core :as muuntaja]
   [reitit.core :as reitit]
   [reitit.ring]
   [reitit.ring.middleware.parameters]
   [reitit.ring.middleware.muuntaja]
   [ring.util.response :as response]
   [ring.middleware.cookies]
   [ring.middleware.file]
   [runner.web.server-render :as server-render]
   [reitit.spec :as rs]
   [reitit.ring.spec :as rrs]
   [expound.alpha :as expound]
   [reitit.ring.middleware.dev]
   [env]
   [page.index]
   ))


(defonce body-script-modules-reference (server-render/body-script-modules-reference (:server-render/body-script-modules-file env/dev)))
(defonce webpack-asset-manifest-reference (server-render/webpack-asset-manifest-reference (:server-render/webpack-asset-manifest-file env/dev)))


(def ring-handler-0
  (reitit.ring/ring-handler
    (reitit.ring/router
      (reitit/routes page.index/router)
      {:validate    rrs/validate
       ::rs/explain expound/expected-str
       ;; :reitit.middleware/transform identity
       :data        {:system/web-dir                       "gh-pages"
                     :server-render/body-script-modules    body-script-modules-reference
                     :server-render/webpack-asset-manifest webpack-asset-manifest-reference
                     :muuntaja                             muuntaja/instance
                     :middleware                           [reitit.ring.middleware.muuntaja/format-middleware]}})
    (reitit.ring/routes
      (reitit.ring/create-default-handler))
    {:middleware [ ;; #(user.ring/wrap-transform-request
                  ;;    %
                  ;;    (fn
                  ;;      [request]
                  ;;      (update-in request [:headers "cookie"]
                  ;;        (fn [cookie]
                  ;;          (if (vector? cookie)
                  ;;            (str/join "; " cookie)
                  ;;            cookie)))))
                  #(ring.middleware.file/wrap-file % "gh-pages")]}))


(def ring-handler #'ring-handler-0)
