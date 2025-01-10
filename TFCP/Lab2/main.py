from enum import Enum


import numpy as np
import matplotlib.pyplot as plt


class MappingType(Enum):
    DIRECT = 1,
    REVERSE = 2


class ConformalMapping:
    def __init__(self, mtype: MappingType):
        self.__options_init__()
        self.__area_init__()
        self.__display_init__(mtype)
        self.__plot_init__()

    def __options_init__(self):
        self.PLOT_SIZE = 5
        self.PLOT_ROWS = 2
        self.PLOT_COLUMNS = 3

        self.EPS = 1e-5

        self.POINT_SIZE = 1
        self.POINT_AMOUNT = 1000

        self.X_MIN = -20
        self.X_MAX = 20
        self.X_VIEW_MIN = -4
        self.X_VIEW_MAX = 4


        self.Y_MIN = -20
        self.Y_MAX = 20
        self.Y_VIEW_MIN = -4
        self.Y_VIEW_MAX = 4

    def __area_init__(self):
        x = np.linspace(self.X_MIN, self.X_MAX, self.POINT_AMOUNT)
        y = np.linspace(self.Y_MIN, self.Y_MAX, self.POINT_AMOUNT)
        xar, yar = np.meshgrid(x, y)
        self.area = xar + 1j * yar

    def __display_init__(self, mtype):
        self.display = dict()
        self.__direct_mapping__() if mtype == MappingType.DIRECT else self.__reverse_mapping__()

    def __direct_mapping__(self):
        self.display["z"] = dict(order=0, area=self.area[self.area.imag + self.area.real + 1 <= self.EPS], color="yellow")
        self.display["w1"] = dict(order=1, func="z + 1/sqrt(2) e^(ipi/4)", area=self.display["z"]["area"] + 1 / np.sqrt(2) * np.exp(1j * np.pi / 4), color="blue")
        self.display["w2"] = dict(order=2, func="e^(-3ipi/4) w1", area=np.exp(-3j * np.pi / 4) * self.display["w1"]["area"], color="red")
        self.display["w3"] = dict(order=3, func="arcch(w2)", area=np.arccosh(self.display["w2"]["area"]), color="green")
        self.display["w4"] = dict(order=4, func="i/pi w3 + 1", area=1j / np.pi * self.display["w3"]["area"] + 1, color="purple")
        self.display["w"] = dict(order=5, func="-2/w4", area=-2 / (self.display["w4"]["area"] - 1), color="orange") # -2/(w4-1)

    def __reverse_mapping__(self):
        self.display["z"] = dict(order=0, area=self.area[(self.area.real >= self.EPS) & (self.area.imag >= self.EPS) & (np.abs(self.area - 1) - 1 >= self.EPS)], color="yellow")
        self.display["w1"] = dict(order=1, func="-2/z", area=-2 / self.display["z"]["area"] + 1, color="blue") # (z-2)/z
        self.display["w2"] = dict(order=2, func="pi/i (w1 - 1)", area=np.pi / 1j * (self.display["w1"]["area"] - 1), color="red")
        self.display["w3"] = dict(order=3, func="ch(w2)", area=np.cosh(self.display["w2"]["area"]) - 1, color="green")  # -1 для beauty
        self.display["w4"] = dict(order=4, func="e^(3ipi/4) w3", area=np.exp(3j * np.pi / 4) * self.display["w3"]["area"], color="purple")
        self.display["w"] = dict(order=5, func="w4 - 1/sqrt(2) e^(ipi/4)", area=self.display["w4"]["area"] - 1 / np.sqrt(2) * np.exp(1j * np.pi / 4), color="orange")

    def __plot_init__(self):
        size, rows, columns = self.PLOT_SIZE, self.PLOT_ROWS, self.PLOT_COLUMNS

        fig, axs = plt.subplots(rows, columns, figsize=(columns * size, rows * size))

        for func, body in self.display.items():
            ordr, ar, clr = body["order"], body["area"], body["color"]

            ax = axs[ordr // columns, ordr % columns]

            ax.scatter(ar.real, ar.imag, color=clr, s=size, label=f"{func} = {body["func"]}" if body.get("func") else func)

            ax.set_xlabel("x")
            ax.set_xlim(self.X_VIEW_MIN, self.X_VIEW_MAX)

            ax.set_ylabel("iy")
            ax.set_ylim(self.Y_VIEW_MIN, self.Y_VIEW_MAX)

            ax.legend()
            ax.grid(True)

        plt.tight_layout()
        plt.show()


ConformalMapping(MappingType.DIRECT)
ConformalMapping(MappingType.REVERSE)
