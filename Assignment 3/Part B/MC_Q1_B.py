import pandas
import matplotlib.pyplot
from pmdarima import auto_arima


if __name__ == '__main__':

    for i in range(3):
        data = pandas.read_csv(f'C:\\Users\\shubh\\Downloads\\Orientation_DB-orientation_data_{i + 1}.csv',
                               sep=';', converters={'time': str, 'pitch': str, 'roll': str, 'yaw': str})

        data['pitch'] = pandas.to_numeric(data['pitch'])
        data['roll'] = pandas.to_numeric(data['roll'])
        data['yaw'] = pandas.to_numeric(data['yaw'])
        data['time'] = pandas.to_datetime(
            data['time'].astype(float), unit='ms')

        predicted_pitch_values = auto_arima(data['pitch'], seasonal=False, trace=True).fit(
            data['pitch']).predict(n_periods=10)
        predicted_roll_values = auto_arima(data['roll'], seasonal=False, trace=True).fit(
            data['roll']).predict(n_periods=10)
        predicted_yaw_values = auto_arima(data['yaw'], seasonal=False, trace=True).fit(
            data['yaw']).predict(n_periods=10)

        figure, graph = matplotlib.pyplot.subplots(figsize=(10, 6))

        graph.plot(data['time'], data['pitch'],
                   label='Pitch (Actual)', color='red')
        graph.plot(data['time'], data['roll'],
                   label='Roll (Actual)', color='green')
        graph.plot(data['time'], data['yaw'],
                   label='Yaw (Actual)', color='blue')

        graph.plot(pandas.date_range(start=data['time'].iloc[-1], periods=11, freq='s')[
                   1:], predicted_pitch_values, label='Pitch (Predicted)', linestyle='dotted', color='red')
        graph.plot(pandas.date_range(start=data['time'].iloc[-1], periods=11, freq='s')[
                   1:], predicted_roll_values, label='Roll (Predicted)', linestyle='dotted', color='green')
        graph.plot(pandas.date_range(start=data['time'].iloc[-1], periods=11, freq='s')[
                   1:], predicted_yaw_values, label='Yaw (Predicted)', linestyle='dotted', color='blue')

        graph.set_xlabel('Time')
        graph.set_ylabel('Values')
        graph.set_title('Actual vs Predicted Values')
        graph.legend()
        matplotlib.pyplot.show()
