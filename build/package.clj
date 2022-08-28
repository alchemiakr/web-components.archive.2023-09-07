(ns build.package
  (:require
   [clojure.xml]
   [clojure.string :as str]
   [clojure.java.io :as jio]
   [clojure.java.shell :as jsh]
   [clojure.tools.build.api :as build]
   [user.java.time.script.print-chrono-version :refer [chrono-version-str]]
   ))


(def version (chrono-version-str))
(def class-dir "target/classes")
(def basis (build/create-basis {:project "deps.edn"}))


(def github-scm-url
  (let [github-server-url (System/getenv "GITHUB_SERVER_URL")
        github-repository (System/getenv "GITHUB_REPOSITORY")]
    (when-not (or (str/blank? github-server-url) (str/blank? github-repository))
      (str github-server-url "/" github-repository))))


(defn clean
  [_]
  (build/delete {:path "target"}))


(defn jar
  [{:keys [lib scm-url]}]
  (build/write-pom {:class-dir class-dir
                    :lib       lib
                    :version   version
                    :basis     basis
                    :scm       {:url (or scm-url github-scm-url "")}
                    :src-dirs  ["src/core"]})
  (build/copy-file {:src    (build/pom-path {:class-dir class-dir :lib lib})
                    :target "pom.xml"})
  (build/copy-dir {:src-dirs   ["src/core"]
                   :target-dir class-dir})
  (build/jar {:class-dir class-dir
              :jar-file  "target/package.jar"}))


(defn- sh-exit!
  [{:keys [exit out err] :as sh-return}]
  (println (str err out))
  (when-not (zero? exit)
    (throw (ex-info "Non-zero exit." sh-return))))


(defn- read-version-from-pom
  []
  (some
    (fn [{:keys [tag] :as elem}] (and (= tag :version) (first (:content elem))))
    (:content (clojure.xml/parse (jio/file "pom.xml")))))


(defn npm-version
  [_]
  (sh-exit! (jsh/sh "npm" "version" (read-version-from-pom) "--no-commit-hooks" "--no-git-tag-version")))


(defn npm
  [{:as opts}]
  (npm-version opts)
  (build/copy-dir {:src-dirs   ["src/core"]
                   :target-dir "dist"
                   :include    "**.{css}"})
  (build/copy-dir {:src-dirs   ["src/core"]
                   :target-dir "dist"
                   :include    "**/rum.{clj,cljs,cljc}"}) ; tailwindcss content
  (build/copy-dir {:src-dirs   ["src/core"]
                   :target-dir "dist"
                   :include    "**/component.{clj,cljs,cljc}"}) ; tailwindcss content
  (build/copy-file {:src "package.json" :target "dist/package.json"})
  ;; (build/copy-file {:src ".npmrc" :target "dist/.npmrc"})
  ;; (build/copy-file {:src ".npmignore" :target "dist/.npmignore"})
  (binding [jsh/*sh-dir* "dist"]
    (let [{:keys [_out] :as ret} (jsh/sh "npm" "pack")]
      (sh-exit! ret)
      #_(sh-exit! (jsh/sh "npm" "publish" (str/trim-newline out))))))
