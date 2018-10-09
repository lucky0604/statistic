import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {Bar} from 'react-chartjs-2'
import palette from '../../lib/color'
import './SolarRadianceChart.css'

class SolarRadianceChart extends Component {
  constructor(props) {
    super(props)

    const xAxisLabel = 'Panel'
    const yAxisLabel = 'kW/㎡'

    this.seriesLabel = 'Solar Radiance'

    this.options = {
      maintainAspectRatio: false,
      scales: {
        xAxes: [{
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
          ticks: {
            beginToZero: true,
            suggestedMax: 1
          }
        }]
      },
      hover: {
        animationDuration: 0
      },
      tooltips: {
        callbacks: {
          // Display the series label as the tooltip title
          title: (tooltipItem, data) => {
            const datasetIndex = tooltipItem[0].datasetIndex
            return data.datasets[datasetIndex].label
          },
          // Display a truncated version of the bar value as the tooltip value
          label: (tooltipItem, data) => {
            const barValue = tooltipItem.yLabel
            const decimalPlaces = 2
            return barValue.toFixed(decimalPlaces) + 'kW/㎡'
          }
        }
      }
    }

    this.legend = {
      display: false
    }
  }

  render() {
    const panels = this.props.panels
    const panelIds = []
    const inputRadiances = []
    const barColors = []
    panels.forEach((panel) => {
      inputRadiances.push(panel.inputRadianceKWM2)
      panelIds.push(panel.id)
      barColors.push((panel.enabled ? palette.lightGreen.toString() : palette.lightGray.setAlpha(0.1).toString()))
    })

    // construct the `data` object in the format the `Bar` component objects
    const data = {
      labels: panelIds,
      datasets: [{
        label: this.seriesLabel,
        data: inputRadiances,
        backgroundColor: barColors,
        borderColor: barColors,
        borderWidth: 1
      }]
    }

    return (
      <div className='solar-radiance-chart--chart-wrapper'>
        <Bar data={data} options={this.options} legend={this.legend} />
      </div>
    )
  }
}

SolarRadianceChart.propTypes = {
  panels: PropTypes.array.isRequired
}

export default SolarRadianceChart