(ns BrowserAnalysis.core)

(use '(incanter core charts excel stats datasets))

(defn main [& args]
 (with-data
   (read-xls "browsersSimple.xls")
   (let [sum (sum ($ :Total))
         percentage (map #(/ %1 sum) ($ :Total))]
     (view (xy-plot (range 1 100) percentage)))
))


(defn is-browser? [browser]
  (= browser))

(defn a-b [& args]
 (with-data
   (read-xls "browsersSimple.xls")
   (let [grand-total (sum ($ :Total))
         percentage (map #(* 100 (/ %1 grand-total)) ($ :Total))
         ie (sum ($ :Total ($where (fn [row] (= (:Browser row) "Microsoft Internet Explorer")))))]
     (str  ie))
))

(defn a-b3 [& args]
 (with-data
   ($rollup :sum :Total [:Browser] (read-xls "browsersSimple.xls"))
   (let [grand-total (sum ($ :Total))
         percentage (map #(/ %1 grand-total) ($ :Total))]
   (view (bar-chart :Browser percentage :eye :legend true)))))


(defn cars1 []
  (with-data
    (get-dataset :cars)
    (let [lm (linear-model ($ :dist) ($ :speed))]
      (view $data)
       (doto (scatter-plot ($ :speed) ($ :dist))
         view
         (add-lines ($ :speed) (:fitted lm)))  )
    )
  )


(defn cars2 []
  (with-data (get-dataset :cars)
    (view ($map (fn [s] (/ s)) :speed))
    (view ($map (fn [s d] (/ s d)) [:speed :dist])))
  )

(defn rup []
  (let [hair-eye-color (get-dataset :hair-eye-color)]
    (with-data ($rollup :mean :count [:hair :eye] hair-eye-color)
    (view (bar-chart :hair :count :group-by :eye :legend true))))
  
  (with-data (->>  (get-dataset :hair-eye-color)
                   ($where {:hair {:in #{"brown" "blond"}}})
                   ($rollup :sum :count [:hair :eye])
                   ($order :count :desc))
    (view $data)
    (view (bar-chart :hair :count :group-by :eye :legend true))))