(ns BrowserAnalysis.core)

(use '(incanter core charts excel stats))

; Working without percentage.
(defn main [& args]
 (with-data
   (read-xls "browsersSimple.xls")
   (let [sum (sum ($ :Total))
         percentage (map #(/ %1 sum) ($ :Total))]
     (view (xy-plot (range 1 100) percentage)))
))
