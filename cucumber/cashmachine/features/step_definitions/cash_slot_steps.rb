Then /^\$(#{CAPTURE_A_NUMBER}) should be dispensed$/ do |amount|
  cash_slot.contents.should == amount
end