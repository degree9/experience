(ns degree9.ui.forms
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [degree9.debug :as dbg]
            [uikit-hl.form :as form]
            [uikit-hl.flex :as flex]
            [uikit-hl.grid :as grid]
            [uikit-hl.margin :as margin]
            [uikit-hl.width :as width]
            [degree9.regex :as regex]))

(dbg/defdebug debug "degree9:experience:forms")

(defmulti form! (fn [data] (keyword (:type data))) :default :input)

(h/defelem form [attr kids]
  (form/form attr
    (map form! kids)))

(defmethod form! :fieldset [{:keys [legend fields] :as data}]
  (let [data (dissoc data :legend :fields :type)]
    (form/fieldset
      (form/legend legend)
      (grid/grid data
        (map form! fields)))))

(defmethod form! :input [{:keys [label] :as data}]
  (let [data (dissoc data :label)]
    (grid/cell
      (form/label label)
      (form/controls
        (form/input data)))))

(defmethod form! :textarea [{:keys [label] :as data}]
  (let [data (dissoc data :label)]
    (grid/cell
      (form/label label)
      (form/controls
        (form/textarea data)))))

(defmethod form! :range [{:keys [label] :as data}]
  (let [data (dissoc data :label)]
    (grid/cell
      (form/label label)
      (form/controls
        (form/range data)))))

(defmethod form! :select [{:keys [label options] :as data}]
  (let [data (dissoc data :label :options)]
    (grid/cell
      (form/label label)
      (form/controls
        (form/select data
          (map h/option options))))))

(defmethod form! :checkbox [{:keys [label options] :as data}]
  (let [data (dissoc data :label :options)]
    (grid/cell
      (form/label label)
      (form/controls ::form/controls-text true
        (h/label (map form/checkbox options))))))

(defmethod form! :radio [{:keys [label options] :as data}]
  (let [data (dissoc data :label :options)]
    (grid/cell
      (form/label label)
      (form/controls ::form/controls-text true
        (h/label (map form/radio options))))))

;;;;;;;;

(h/defelem input [{:keys [value validate] :as attr} kids]
  (let [valid   (j/cell= (boolean validate))
        success (j/cell= (when value valid))
        failure (j/cell= (when value (not valid)))]
    (j/cell= (debug "Validate form input %s (%s)" validate valid))
    (form/input
      (dissoc attr :validate)
      :success success
      :danger  failure
      kids)))

(h/defelem number [attr kids]
  (input
    attr
    :type "number"
    :pattern regex/number
    kids))

(h/defelem email [attr kids]
  (input
    attr
    :type "email"
    :pattern regex/email
    kids))

(h/defelem phone-number [attr kids]
  (input
    attr
    :type "tel"
    :pattern regex/phone-number
    kids))
