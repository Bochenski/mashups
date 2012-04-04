When /^I withdraw \$(#{CAPTURE_A_NUMBER})$/ do |amount|
  teller.withdraw_from(my_account,amount)
end