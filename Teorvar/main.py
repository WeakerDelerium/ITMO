import matplotlib.pyplot as plt
import math

# выборка
sample = [
    17.1, 21.4, 15.9, 19.1, 22.4, 20.7, 17.9, 18.6, 21.8, 16.1,
    19.1, 20.5, 14.2, 16.9, 17.8, 18.1, 19.1, 15.8, 18.8, 17.2,
    16.2, 17.3, 22.5, 19.9, 21.1, 15.1, 17.7, 19.8, 14.9, 20.5,
    17.5, 19.2, 18.5, 15.7, 14.0, 18.6, 21.2, 16.8, 19.3, 17.8,
    18.8, 14.3, 17.1, 19.5, 16.3, 20.3, 17.9, 23.0, 17.2, 15.2,
    15.6, 17.4, 21.3, 22.1, 20.1, 14.5, 19.3, 18.4, 16.7, 18.2,
    16.4, 18.7, 14.3, 18.2, 19.1, 15.3, 21.5, 17.2, 22.6, 20.4,
    22.8, 17.5, 20.2, 15.5, 21.6, 18.1, 20.5, 14.0, 18.9, 16.5,
    20.8, 16.6, 18.3, 21.7, 17.4, 23.0, 21.1, 19.8, 15.4, 18.1,
    18.9, 14.7, 19.5, 20.9, 15.8, 20.2, 21.8, 18.2, 21.2, 20.1
]


# n log n сортировка
def quick_sort(arr):
    if len(arr) <= 1:
        return arr

    pivot = arr[len(arr) // 2]
    left, eq, right = [], [], []

    for x in arr:
        if x < pivot:
            left.append(x)
        elif x == pivot:
            eq.append(x)
        else:
            right.append(x)

    return quick_sort(left) + eq + quick_sort(right)


# получение статического ряда
def get_static_series(arr):
    series = {}

    for x in arr:
        series[x] = series[x] + 1 if x in series else 1

    return list(series.items())


# вариационный и статический ряды
var_series = quick_sort(sample)
var_volume = len(var_series)

st_series = get_static_series(var_series)
st_series_vals = [val for val, freq in st_series]
st_series_freq = [freq for val, freq in st_series]
st_series_prob = [freq / var_volume for val, freq in st_series]

# экстремальные значения
var_series_min = var_series[0]
var_series_max = var_series[-1]

# размах
var_series_scope = var_series_max - var_series_min

# выборочное среднее
sample_mean = sum(var_series) / var_volume

# выборочная и исправленная дисперсия
sample_variance = sum((x - sample_mean) ** 2 * n for x, n in st_series) / var_volume
corrected_variance = var_volume / (var_volume - 1) * sample_variance

# выборочное и исправленное ско
sample_std_dev = sample_variance ** 0.5
corrected_std_dev = corrected_variance ** 0.5

# эмпирическая функция распределения
emp_func = [0]
emp_values = [n / var_volume for _, n in st_series]
for ind in range(len(emp_values)):
    emp_func.append(round(emp_func[-1] + emp_values[ind], 2))

# интервалы через формулу Стерджеса или l = const + перерасчет стартового значения
# int_count = math.ceil(1 + math.log2(var_volume))
int_count = 9

int_width = var_series_scope / int_count

# int_start = var_series_min - int_width / 2
int_start = var_series_min

intervals = [int_start + i * int_width for i in range(int_count + 1)]
intervals = [(fst, snd) for fst, snd in zip(intervals, intervals[1:])]
int_centers = [(fst + snd) / 2 for fst, snd in intervals]

# высчитываем попадания в области
int_freq = [0] * int_count
for x in var_series:
    ind = min(int((x - int_start) / int_width), int_count - 1)
    int_freq[ind] += 1

int_prob = [val / var_volume for val in int_freq]

# построение гистограммы (с переводом частоты)
plt.bar(int_centers, int_prob, width=int_width * 0.9)
plt.xlabel('Значение')
plt.ylabel('Частота')
plt.title('Гистограмма распределения')
plt.show()

# построение полигоны частоты
plt.plot(int_centers, int_prob, marker='o', color='red')
plt.xlabel('Значение')
plt.ylabel('Частота')
plt.title('Полигон частот')
plt.show()

# построение эфр
# построение эфр
for i in range(len(st_series_vals) - 1):
    plt.hlines(y=emp_func[i], xmin=st_series_vals[i], xmax=st_series_vals[i + 1], color='black', linestyle='-')
plt.xlabel('Значение выборки')
plt.ylabel('Эмпирическая вероятность')
plt.title('Эмпирическая функция распределения')
plt.show()

print('Начальный ряд:')
for dec in [sample[i: i + 10] for i in range(0, var_volume, 10)]:
    print('--| ', end='')
    print(*dec)

print('\nВариационный ряд:')
for dec in [var_series[i: i + 10] for i in range(0, var_volume, 10)]:
    print('--| ', end='')
    print(*dec)

print(f'\nСтатический ряд:')
for val, freq, prob in zip(st_series_vals, st_series_freq, st_series_prob):
    print(f'--| {val}\t{freq}\t{prob}')


print(f'\nЭкстремальные значения:')
print(f'--> Минимум: {var_series_min}')
print(f'--> Максимум {var_series_max}\n')

print(f'Размах: {var_series_scope}\n')
print(f'Мода: ')
print(f'Медиана: ')

print(f'Выборочное среднее: {sample_mean}\n')

print(f'Дисперсия:')
print(f'--> Выборочная: {sample_variance}')
print(f'--> Исправленная: {corrected_variance}\n')

print(f'Среднее квадратичное отклонение:')
print(f'--> Выборочное: {sample_std_dev}')
print(f'--> Исправленное: {corrected_std_dev}\n')

print(f'Эмпирическая функция распределения:')
print(f'--> {emp_func[0]} при x <= {st_series_vals[0]}')
for i in range(1, len(st_series)):
    print(f'--> {emp_func[i]} при {st_series_vals[i - 1]} < x <= {st_series_vals[i]}')
print(f'--> {emp_func[-1]} при {st_series_vals[-1]} < x\n')

print('Интервалы:')
print(f'--> Количество: {int_count}')
print(f'--> Длина: {int_width}')
print(f'--> Интервальное распределение:')
print('\t\t\tИнтервал\tЧастота')
for i in range(int_count):
    print(f'--| {i}\t' +
          '[' + ', '.join([str(round(num, 2)) for num in intervals[i]]) + (']' if i == int_count - 1 else ')') +
          f'\t\t{int_freq[i]}')
