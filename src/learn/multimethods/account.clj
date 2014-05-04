(ns learn.multimethods.account)
(alias 'acc 'learn.multimethods.account)

(defstruct account
  :id
  :tag
  :balance)

(def test-savings (struct account 1 ::acc/Savings 100M))
(def test-checking (struct account 2 ::acc/Checking 250M))

(defmulti interest-rate :tag)
(defmethod interest-rate ::acc/Checking [_] 0M)
(defmethod interest-rate ::acc/Savings  [_] 0.05M)

(interest-rate test-savings)
(interest-rate test-checking)

(defmulti account-level :tag)
(defmethod account-level ::acc/Checking [acct]
  (if (>= (:balance acct) 5000) ::acc/Premium ::acc/Basic))
(defmethod account-level ::acc/Savings [acct]
  (if (>= (:balance acct) 1000) ::acc/Premium ::acc/Basic))

(account-level (struct account 1 ::acc/Savings 2000M))
(account-level (struct account 1 ::acc/Checking 2000M))

(defmulti service-charge #([(account-level %) (:tag %)]))
(defmethod service-charge [::acc/Basic ::acc/Checking]   [_] 25)
(defmethod service-charge [::acc/Basic ::acc/Savings]    [_] 10)
(defmethod service-charge [::acc/Premium ::acc/Checking] [_] 0)
(defmethod service-charge [::acc/Premium ::acc/Savings]  [_] 0)

(service-charge {:tag ::acc/Checking :balance 1000M})
(service-charge {:tag ::acc/Savings :balance 1000M})
