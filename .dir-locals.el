((nil
  (cider-default-cljs-repl . shadow)
  (cider-shadow-default-options . "app")
  (cider-shadow-watched-builds . ("app"))
  ;; user.clj를 사용하면 CI 환경에서 hang 걸리는 이슈가 있다.
  (cider-repl-init-code . ("(clojure.core/apply clojure.core/require clojure.main/repl-requires)"
			   "(load \"/init_user\")"
			   ))
  )
 (clojure-mode
  (cider-preferred-build-tool . shadow-cljs)
  (cider-clojure-cli-aliases . ":test:rebl")
  (clojure-local-source-test-path . "src/test")
  ))
