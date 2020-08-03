# importing the dependencies
from flask import Flask,render_template,request, url_for, jsonify
from PIL import Image
import io
from prediction import predict

app = Flask(__name__)

@app.route('/', methods=['GET'])
def home():
	if request.method == 'GET':
		return render_template('index.html')

@app.route('/predict', methods=['POST'])
def response():
	if request.method == 'POST':
		if 'file' not in request.files:
			return "Image not uploaded"
		file = request.files['file'].read()

		try:
			img = Image.open(io.BytesIO(file))
		except IOError:
			return "<p>Not an image</p>"
		
		img = img.convert("RGB")
		# img.save("input.jpg")
		result = predict(img)
		return jsonify(result)

if __name__ == '__main__':
    app.run()