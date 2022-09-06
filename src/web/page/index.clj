(ns page.index
  (:require
   [clojure.java.io :as jio]
   [rum.core :as rum]
   [citrus.core :as citrus]
   [reitit.core :as reitit]
   [reitit.ring]
   [runner.web.server-render :as server-render]
   [page.components]
   ))


(defonce reconciler
  (citrus/reconciler
    {:state (atom nil)}))


(def router
  (reitit/router
    [""
     {:middleware [[server-render/wrap-body-scripts "main"]
                   [server-render/wrap-html-request :html/scripts
                    [:script {:dangerouslySetInnerHTML {:__html (slurp (jio/resource "alchemiakr/head.js"))}}]]
                   [server-render/wrap-html-request :html/scripts
                    [:script {:async true :src "https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3733725353908789" :crossorigin "anonymous"}]]
                   [server-render/wrap-html-request :html/stylesheets (fnil into [])
                    [[:link {:rel "stylesheet" :href "https://fonts.googleapis.com/icon?family=Material+Icons"}]]]
                   [server-render/wrap-webpack-asset-stylesheets ["core.css"]]]}
     ["/"
      (fn [request]
        (-> request
          (assoc :html/contents
            [[:#header
              (page.components/header reconciler)]
             [:#mount
              (page.components/examples)]
             [:aside]])
          (server-render/html-response)))]
     ["/table"
      (fn [request]
        (-> request
          (assoc :html/contents
            [[:main]
             [:aside]])
          (server-render/html-response)))]]))
