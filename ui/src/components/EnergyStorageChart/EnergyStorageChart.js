import React, {Component} from 'react'
import {Bar} from 'react-chartjs-2'
import PropTypes from 'prop-types'
import palette from '../../lib/color'
import './EnergyStorageChart.css'

class EnergyStorageChart extends Component {
  constructor(props) {
    super(props)
    const xAxisLabel = 'Battery'
    const yAxisLabel = 'kW·h'
    this.occupiedBarLabel = 'Energy Stored'
    this.vacantBarLabel = 'Free Space'

    this.occupiedBarBackgroundColors = [
      palette.lightGreen.toString(),
      palette.lightGreen.toString(),
      palette.lightGreen.toString()
    ]

    this.vacantBarBackgroundColors = [
      palette.lightGray.setAlpha(0.1).toString(),
      palette.lightGray.setAlpha(0.1).toString(),
      palette.lightGray.setAlpha(0.1).toString()
    ]

    this.options = {
      maintainAspectRatio: false,
      hover: {
        animationDuration: 0
      },
      scales: {
        xAxes: [{
          stacked: true,
          scaleLabel: {
            display: true,
            labelString: xAxisLabel
          },
          gridLines: {
            display: false
          }
        }],
        yAxes: [{
          scaleLabel: {
            display: true,
            labelString: yAxisLabel
          },
          stacked: true,
          ticks: {
            beginAtZero: true,
            suggestedMax: 15
          }
        }]
      },
      tooltips: {
        callbacks: {
          title: (tooltipItem, data) => {
            const datasetIndex = tooltipItem[0].datasetIndex
            return data.datasets[datasetIndex].label
          },
          label: (tooltipItem, data) => {
            const barValue = tooltipItem.yLabel
            return barValue.toFixed(1) + ' kW·h'
          }
        }
      }
    }
    this.legend = {
      display: false
    }
  }

  render() {
    const batteries = this.props.batteries
    const batteryIds = []
    const storedEnergiesKWh = []
    const vacanciesKWh = []

    batteries.forEach((battery) => {
      const storedEnergyKWh = battery.storedEnergyKWh
      const vacancyKWh = battery.energyCapacityKWh - storedEnergyKWh

      batteryIds.push(battery.id)
      storedEnergiesKWh.push(storedEnergyKWh)
      vacanciesKWh.push(vacancyKWh)
    })

    const data = {
      labels: batteryIds,
      datasets: [{
        label: this.occupiedBarLabel,
        data: storedEnergiesKWh,
        backgroundColor: this.occupiedBarBackgroundColors,
        borderColor: this.occupiedBarBackgroundColors,
        borderWidth: 1
      }, {
        label: this.vacantBarLabel,
        data: vacanciesKWh,
        backgroundColor: this.vacantBarBackgroundColors,
        borderColor: this.vacantBarBackgroundColors,
        borderWidth: 1
      }]
    }

    return (
      <div className='energy-storage-chart--chart-wrapper'>
        <Bar data={data} options={this.options} legend={this.legend} />
      </div>
    )
  }
}

EnergyStorageChart.propTypes = {
  batteries: PropTypes.array.isRequired
}

export default EnergyStorageChart