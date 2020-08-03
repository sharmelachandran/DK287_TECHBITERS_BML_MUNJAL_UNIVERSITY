from detecto import utils, visualize, core

def predict(img):
	model = core.Model()

	predictions = model.predict_top(img)
	labels, boxes, scores = predictions

	l = labels
	s = []
	for i in scores:
		s.append(i.item())
	result = {"labels":l, "scores":s}

	return result
