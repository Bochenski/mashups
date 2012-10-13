function [C, sigma] = dataset3Params(X, y, Xval, yval)
%EX6PARAMS returns your choice of C and sigma for Part 3 of the exercise
%where you select the optimal (C, sigma) learning parameters to use for SVM
%with RBF kernel
%   [C, sigma] = EX6PARAMS(X, y, Xval, yval) returns your choice of C and 
%   sigma. You should complete this function to return the optimal C and 
%   sigma based on a cross-validation set.
%

% You need to return the following variables correctly.
C = 0.01;
sigma = 0.01;

% ====================== YOUR CODE HERE ======================
% Instructions: Fill in this function to return the optimal C and sigma
%               learning parameters found using the cross validation set.
%               You can use svmPredict to predict the labels on the cross
%               validation set. For example, 
%                   predictions = svmPredict(model, Xval);
%               will return the predictions on the cross validation set.
%
%  Note: You can compute the prediction error using 
%        mean(double(predictions ~= yval))
%
bestFit = 100000000;
C_i = 0.01;
sigma_j = 0.01;

for i = 1 : 8
	for j = 1 : 8	
		C_i
		sigma_j
		%generate a model for the chosen values of c and sigma
		model = svmTrain(X, y, C_i, @(x1, x2) gaussianKernel(x1, x2, sigma_j));
		predictions = svmPredict(model, Xval);
		prediction_error = mean(double(predictions ~= yval))
		if prediction_error < bestFit
			C = C_i
			sigma = sigma_j
			bestFit = prediction_error
		end
		if (mod(j,2) == 1)
			sigma_j = sigma_j * 3;
		else
			sigma_j = (sigma_j /3) * 10;
		end
	end
	sigma_j = 0.01;
	if (mod(i,2) == 1)
		C_i = C_i * 3;
	else
		C_i = (C_i / 3) * 10;
	end
end 

C
sigma


% =========================================================================

end
