interface AccountTableRowProps {
  index: number
  listIndex: Props
  isItemChecked: (index: number) => boolean
  toggleCheckbox: (index: number) => void
  handleToggleModal: (
    index: number,
    accountId: string,
    scanTime: string,
    accountName: string,
  ) => void
}

interface Props {
  client: string
  accountName: string
  accountId: string
  registerTime: string
  lastUpdateDescribeTime: string
  describeResult: string
}

export default function AccountTableRow({
  index,
  listIndex,
  isItemChecked,
  toggleCheckbox,
  handleToggleModal,
}: AccountTableRowProps) {
  return (
    <div
      key={index}
      className='grid h-10 w-full grid-cols-13 items-center text-nowrap border-b-2 border-gray-300 bg-white text-sm text-black'
    >
      <div className='flex h-full w-full items-center justify-center overflow-hidden border-r '>
        <input
          id={`acount-${index}-checkbox`}
          name={`acount-${index}-checkbox`}
          type='checkbox'
          checked={isItemChecked(index)}
          onChange={() => {
            toggleCheckbox(index)
          }}
        />
      </div>
      <div className='col-span-2 flex h-full w-full items-center justify-center overflow-hidden border-r'>
        {listIndex.client}
      </div>
      <div className='col-span-2 flex h-full w-full items-center justify-center overflow-hidden border-r'>
        {listIndex.accountName}
      </div>
      <div className='col-span-2 flex h-full w-full items-center justify-center overflow-hidden border-r'>
        {listIndex.accountId}
      </div>
      <div className='col-span-2 flex h-full w-full items-center justify-center overflow-hidden border-r'>
        {listIndex.registerTime}
      </div>

      <div className='col-span-2 flex h-full w-full items-center justify-center overflow-hidden border-r'>
        {listIndex.lastUpdateDescribeTime}
      </div>
      <div
        className={`h-full w-full items-center ${listIndex.describeResult === 'Fail' ? 'text-sky-600 underline underline-offset-1' : ''} col-span-2 flex justify-center overflow-hidden `}
        onClick={() => {
          if (listIndex.describeResult === 'Fail') {
            handleToggleModal(
              index,
              listIndex.accountId,
              listIndex.lastUpdateDescribeTime,
              listIndex.accountName,
            )
          }
        }}
      >
        {listIndex.describeResult}
      </div>
    </div>
  )
}
