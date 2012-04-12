Given /^my account has been credited with \$(#{CAPTURE_A_NUMBER})$/ do |amount|
	my_account.credit(amount)
end

Then /^the balance of my account should be \$(#{CAPTURE_A_NUMBER})$/ do |amount|
  eventually { my_account.reload.balance.should eq(amount), "Expected the balance to be #{amount} but it was #{my_account.balance}" }
end