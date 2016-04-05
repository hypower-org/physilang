(ns physilang.core
  (:require [instaparse.core :as insta])
  (:require [clojure.string :as str])
  (:gen-class :main true))

(def digit "digit = #'[0-9]+' ")
(def letter "letter = #'[a-zA-Z]+' ")

(def numbers "numbers = <digit |
             digit'.'digit | 
             digit'.'digit('e' | 'E')digit> ")

(def id "id = <(letter+ digit*) ('.'letter+ digit*)?> ")
(def ws "<ws> = #'\\s+' ")

(def stm "stm = id '=' (expr | id+) <';'> ")

(def stms "stms = <ws>? stm (<ws> stm)* <ws>? ")
(def if-stm "if = conditions '{' expr '}' ")
(def switch-stm " 'switch' ")

;taken from instaparse page
(def expr "expr = add-sub
          <add-sub> = mul-div | add | sub
          add = add-sub <'+'> mul-div
          sub = add-sub <'-'> mul-div
          <mul-div> = term | mul | div
          mul = mul-div <'*'> term
          div = mul-div <'/'> term
          <term> = (numbers | <'('> add-sub <')'> | id) ")

(def condition "")
(def conditions "")
(def switch-stm "")

(def arg "arg           = <('in' | 'out') ws> id ")
(def args "args         = arg (<',' ws> arg)* ")

(def body "body = <'{'> stms <'}'> ?<ws>")

;building blocks, task, function, resource
(def block "block         = <('Task' | 'Resource' | 'function')> <ws> id <'('> args? <')'> body ")

(def prog "prog = block* ")

(def pl-grammar-ebnf (str prog
                          stms stm 
                          body
                          expr 
                          numbers ws id digit letter 
                          arg args 
                          block))

; Force the use of hiccup for now.
(def pl-grammar
  (insta/parser pl-grammar-ebnf :output-format :hiccup))

(defn -main
  [& args]
  (if (not (empty? args)) 
    (pl-grammar (slurp (first args)))
    ;    (pl-grammar (apply str (str/split (slurp (first args)) #"\s+")))
    (println "No args")))

(load "generator")
