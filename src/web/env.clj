(ns env)


(def dev
  {:system/web-dir                            "gh-pages"
   :web/file-public-root                      "gh-pages"
   :server-render/root-dir                    "server-render"
   :server-render/body-script-modules-file    "server-render/body_script_modules.dev.edn"
   :server-render/webpack-asset-manifest-file "gh-pages/b/assets.dev.json"})


(def release
  {:system/web-dir                            "gh-pages"
   :web/file-public-root                      "gh-pages"
   :server-render/root-dir                    "server-render"
   :server-render/body-script-modules-file    "server-render/body_script_modules.edn"
   :server-render/webpack-asset-manifest-file "gh-pages/b/assets.json"})
