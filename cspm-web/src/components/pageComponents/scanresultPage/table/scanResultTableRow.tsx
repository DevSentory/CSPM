import React from 'react'

interface TableRowProps {
  item: Props
  index: number
  isItemChecked: (index: number) => boolean
  toggleCheckbox: (index: number) => void
  handleToggleModal: (
    index: number,
    resourceId: string,
    scanTime: string,
    accountId: string,
    accountName: string,
  ) => void
  handleResModal: (
    index: number,
    resourceId: string,
    policyTitle: string,
    accountId: string,
    accountName: string,
  ) => void
}

interface Props {
  scanTime: string
  rid: string
  vulnerabilityStatus: string
  accountName: string
  accountId: string
  resourceId: string
  client: string
  policySeverity: string
  policyTitle: string
  policyCompliance: string
}

export default function TableRow({
  item,
  index,
  isItemChecked,
  toggleCheckbox,
  handleToggleModal,
  handleResModal,
}: TableRowProps) {
  return (
    <div
      key={index}
      className={`grid h-10 w-full grid-cols-21 items-center gap-2 text-nowrap break-words border-b-2 border-gray-300`}
    >
      <div className='col-span-1 flex h-full w-full items-center justify-center overflow-hidden border-r'>
        <input
          type='checkbox'
          checked={isItemChecked(index)}
          onChange={() => toggleCheckbox(index)}
        />
      </div>
      <div className='col-span-2 flex h-full w-full items-center justify-center  overflow-hidden border-r text-sm'>
        {item.scanTime}
      </div>
      <div className='col-span-1 flex h-full w-full items-center justify-center overflow-hidden border-r text-sm'>
        {item.rid}
      </div>
      <div className='col-span-3 flex h-full w-full items-center justify-center overflow-hidden border-r text-sm'>
        <div
          className={`flex h-full w-2/5 items-center justify-center overflow-hidden border-r ${item.vulnerabilityStatus === 'Exception' ? 'cursor-pointer text-sky-600 underline underline-offset-1' : ''}`}
          onClick={() => {
            if (item.vulnerabilityStatus === 'Exception') {
              handleResModal(
                index,
                item.resourceId,
                item.policyTitle,
                item.accountId,
                item.accountName,
              )
            }
          }}
        >
          {item.vulnerabilityStatus}
        </div>
        <div
          className={`flex h-full w-3/5 items-center ${item.accountName.length > 11 ? 'ml-1 justify-start' : 'justify-center'} overflow-hidden`}
        >
          {item.accountName}
        </div>
      </div>
      <div className='col-span-2 flex h-full w-full items-center justify-center overflow-hidden border-r text-sm'>
        {item.accountId}
      </div>
      <div className='col-span-3 flex h-full w-full items-center justify-start overflow-hidden border-r text-sm'>
        {item.resourceId}
      </div>
      <div className='col-span-1 flex h-full w-full items-center justify-center overflow-hidden border-r text-sm'>
        {item.client}
      </div>
      <div className='col-span-1 flex h-full w-full items-center justify-center overflow-hidden border-r text-sm'>
        {item.policySeverity}
      </div>
      <div className='col-span-3 line-clamp-1 flex h-full w-full items-center justify-start overflow-hidden border-r text-sm'>
        {item.policyTitle}
      </div>
      <div className='col-span-3 flex h-full w-full items-center justify-start overflow-hidden truncate border-r text-sm'>
        {item.policyCompliance}
      </div>
      <div className='flex  w-full items-center justify-center'>
        <button
          className='h-full w-[90%] rounded bg-kyboNavy text-sm text-white'
          onClick={() => {
            handleToggleModal(
              index,
              item.resourceId,
              item.scanTime,
              item.accountId,
              item.accountName,
            )
          }}
        >
          상세조회
        </button>
      </div>
    </div>
  )
}
